package com.example.userapp.user.data.repository

import com.example.userapp.user.data.api.model.AddressResponse
import com.example.userapp.user.data.api.model.CompanyResponse
import com.example.userapp.user.data.api.model.GeoResponse
import com.example.userapp.user.data.api.model.UserInfoResponse
import com.example.userapp.user.data.db.entity.AddressEntity
import com.example.userapp.user.data.db.entity.CompanyEntity
import com.example.userapp.user.data.db.entity.GeoEntity
import com.example.userapp.user.data.db.entity.UserInfoEntity
import com.example.userapp.user.data.repository.UserConverter.map
import com.example.userapp.user.domain.model.Address
import com.example.userapp.user.domain.model.Company
import com.example.userapp.user.domain.model.Geo
import com.example.userapp.user.domain.model.UserInfo

object UserConverter {

    fun fromNetwork(response: UserInfoResponse): UserInfo {
        return UserInfo(
            id = response.id,
            name = response.name,
            username = response.username,
            email = response.email,
            address = response.address.map(),
            phone = response.phone,
            website = response.website,
            company = response.company.map()
        )
    }

    fun toEntity(model: UserInfo): UserInfoEntity {
        return UserInfoEntity(
            id = model.id,
            name = model.name,
            username = model.username,
            email = model.email,
            address = model.address.map(),
            phone = model.phone,
            website = model.website,
            company = model.company.map()
        )
    }

    fun fromEntity(entity: UserInfoEntity): UserInfo {
        return UserInfo(
            id = entity.id,
            name = entity.name,
            username = entity.username,
            email = entity.email,
            address = entity.address.map(),
            phone = entity.phone,
            website = entity.website,
            company = entity.company.map()
        )
    }

    private fun AddressResponse.map(): Address {
        return Address(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.map()
        )
    }

    private fun AddressEntity.map(): Address {
        return Address(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.map()
        )
    }

    private fun Address.map(): AddressEntity {
        return AddressEntity(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.map()
        )
    }

    private fun GeoResponse.map(): Geo {
        return Geo(
            lat = lat,
            lng = lng
        )
    }

    private fun GeoEntity.map(): Geo {
        return Geo(
            lat = lat,
            lng = lng
        )
    }

    private fun Geo.map(): GeoEntity {
        return GeoEntity(
            lat = lat,
            lng = lng
        )
    }

    private fun CompanyResponse.map(): Company {
        return Company(
            name = name,
            catchPhrase = catchPhrase,
            bs = bs
        )
    }

    private fun CompanyEntity.map(): Company {
        return Company(
            name = name,
            catchPhrase = catchPhrase,
            bs = bs
        )
    }

    private fun Company.map(): CompanyEntity {
        return CompanyEntity(
            name = name,
            catchPhrase = catchPhrase,
            bs = bs
        )
    }
}