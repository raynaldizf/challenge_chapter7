package com.example.challenge_chapter6

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataUser")
class PreferencesLogin(val context: Context) {

    val username = stringPreferencesKey("username")
    val email = stringPreferencesKey("email")
    val password = stringPreferencesKey("password")
    val namaLengkap = stringPreferencesKey("namaLengkap")
    val alamat = stringPreferencesKey("alamat")
    val tanggalLahir = stringPreferencesKey("tanggalLahir")


    suspend fun dataSaveRegister(username: String, email: String, password: String, namaLengkap: String, alamat: String, tanggalLahir: String){
        context.dataStore.edit {
            it[this.username] = username
            it[this.email] = email
            it[this.password] = password
            it[this.namaLengkap] = namaLengkap
            it[this.alamat] = alamat
            it[this.tanggalLahir] = tanggalLahir
        }
    }

    suspend fun updateProfile(username: String, email: String, alamat: String){
        context.dataStore.edit {
            it[this.username] = username
            it[this.email] = email
            it[this.alamat] = alamat
        }
    }

    suspend fun clearData(){
        context.dataStore.edit {
            it.clear()
        }
    }

    val userName: Flow<String> = context.dataStore.data
        .map {
            it[username] ?: ""
        }
    val userEmail: Flow<String> = context.dataStore.data
        .map {
            it[email] ?: ""
        }

    val userPassword: Flow<String> = context.dataStore.data
        .map {
            it[password] ?: ""
        }
    val userNamaLengkap: Flow<String> = context.dataStore.data
        .map {
            it[namaLengkap] ?: ""
        }
    val userAlamat: Flow<String> = context.dataStore.data
        .map {
            it[alamat] ?: ""
        }
    val userTanggalLahir: Flow<String> = context.dataStore.data
        .map {
            it[tanggalLahir] ?: ""
        }


}