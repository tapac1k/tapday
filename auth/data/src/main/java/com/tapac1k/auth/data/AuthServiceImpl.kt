package com.tapac1k.auth.data

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.tapac1k.auth.domain.AuthService
import com.tapac1k.utils.common.resultOf
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : AuthService {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var credentialManager = CredentialManager.create(context)

    override fun init() {
        FirebaseApp.initializeApp(context)
    }

    override fun isUserSignedIn(): Boolean {
        Log.e("TestX", "logged in ${FirebaseAuth.getInstance().currentUser}")
        return FirebaseAuth.getInstance().currentUser != null
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
        scope.launch {
            try {
                val clearRequest = ClearCredentialStateRequest()
                credentialManager.clearCredentialState(clearRequest)
            } catch (e: ClearCredentialException) {
                Log.e("TestX", "Couldn't clear user credentials: ${e.localizedMessage}")
            }
        }
    }

    override fun subscribeUserLoggedOut(): Flow<Unit> {
        return callbackFlow {
            val stateListener = AuthStateListener {
                if (it.currentUser == null) this.trySendBlocking(Unit)
            }
            FirebaseAuth.getInstance().addAuthStateListener(stateListener)
            awaitClose {  FirebaseAuth.getInstance().removeAuthStateListener(stateListener) }
        }
    }

    override suspend fun googleSignIn(): Result<Unit> {
        return resultOf {
            val firebaseAuth = FirebaseAuth.getInstance()

            // Generate a nonce (a random number used once)
            val ranNonce: String = UUID.randomUUID().toString()
            val bytes: ByteArray = ranNonce.toByteArray()
            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            val digest: ByteArray = md.digest(bytes)
            val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }

            // Set up Google ID option
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("1021436916560-db7vi7hu8s9nj8kqp62d206ave0c8t9v.apps.googleusercontent.com")
                .setNonce(hashedNonce)
                .build()

            // Request credentials
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // Get the credential result
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            // Check if the received credential is a valid Google ID Token
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                Log.d("TestX", "authResult: ${authResult.user}")
                FirebaseAuth.getInstance()
                return@resultOf Unit
            } else {
                throw GetCredentialCancellationException("Unsupported credential")
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}
