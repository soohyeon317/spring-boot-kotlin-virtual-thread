package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.appuseenvironment

import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironment
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AppUseEnvironmentRepositoryImpl(
    private val springDataAppUseEnvironmentRepository: SpringDataAppUseEnvironmentRepository,
) : AppUseEnvironmentRepository {

    override fun save(
        appUseEnvironment: AppUseEnvironment,
        willDelete: Boolean?,
    ): AppUseEnvironment {
        return springDataAppUseEnvironmentRepository.save(
            AppUseEnvironmentEntity(
                appUseEnvironment = appUseEnvironment,
                willDelete = willDelete
            )
        ).toAppUseEnvironment()
    }

    override fun findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        accountId: Long,
        deviceModelName: String,
    ): AppUseEnvironment? {
        return springDataAppUseEnvironmentRepository.findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
            accountId = accountId,
            deviceModelName = deviceModelName
        )?.toAppUseEnvironment()
    }

    override fun deleteAllByAccountIdAndDeviceModelNameAndDeletedAtIsNull(
        accountId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime,
    ) {
        return springDataAppUseEnvironmentRepository.deleteAllByAccountIdAndDeviceModelNameAndDeletedAtIsNull(
            accountId = accountId,
            deviceModelName = deviceModelName,
            deletedAt = deletedAt
        )
    }

    override fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime
    ) {
        springDataAppUseEnvironmentRepository.deleteAllByAccountIdAndDeletedAtIsNull(
            accountId = accountId,
            deletedAt = deletedAt
        )
    }


}
