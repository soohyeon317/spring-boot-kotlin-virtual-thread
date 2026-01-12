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
        willDelete: Boolean,
    ): AppUseEnvironment {
        return springDataAppUseEnvironmentRepository.save(
            AppUseEnvironmentEntity(
                appUseEnvironment = appUseEnvironment,
                willDelete = willDelete
            )
        ).toAppUseEnvironment()
    }

    override fun findTopByMemberIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        memberId: Long,
        deviceModelName: String,
    ): AppUseEnvironment? {
        return springDataAppUseEnvironmentRepository.findTopByMemberIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
            memberId = memberId,
            deviceModelName = deviceModelName
        )?.toAppUseEnvironment()
    }

    override fun deleteAllByMemberIdAndDeviceModelNameAndDeletedAtIsNull(
        memberId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime,
    ) {
        return springDataAppUseEnvironmentRepository.deleteAllByMemberIdAndDeviceModelNameAndDeletedAtIsNull(
            memberId = memberId,
            deviceModelName = deviceModelName,
            deletedAt = deletedAt
        )
    }

    override fun deleteAllByMemberIdAndDeletedAtIsNull(
        memberId: Long,
        deletedAt: LocalDateTime
    ) {
        springDataAppUseEnvironmentRepository.deleteAllByMemberIdAndDeletedAtIsNull(
            memberId = memberId,
            deletedAt = deletedAt
        )
    }


}
