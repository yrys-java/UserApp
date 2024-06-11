package com.example.userapp.user.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey 
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("username")
    val username: String,
    @ColumnInfo("email")
    val email: String,
    @Embedded(prefix = "address")
    val address: AddressEntity,
    @Embedded(prefix = "company_")
    val company: CompanyEntity,
    @ColumnInfo("phone")
    val phone: String,
    @ColumnInfo("website")
    val website: String
)

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val addressId: Int = 0,
    @ColumnInfo("street")
    val street: String,
    @ColumnInfo("suite")
    val suite: String,
    @ColumnInfo("city")
    val city: String,
    @ColumnInfo("zipcode")
    val zipcode: String,
    @Embedded(prefix = "_geo_")
    val geo: GeoEntity
)

@Entity(tableName = "geo")
data class GeoEntity(
    @PrimaryKey(autoGenerate = true)
    val geoId: Int = 0,
    @ColumnInfo("lat")
    val lat: String,
    @ColumnInfo("lng")
    val lng: String
)

@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val companyId: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("catchPhrase")
    val catchPhrase: String,
    @ColumnInfo("bs")
    val bs: String
)