package com.tapac1k.training.contract

interface SyncDatabaseWithFirebase {
    suspend fun invoke()
}