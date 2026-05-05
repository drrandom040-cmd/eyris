package com.elsewhere.eyris.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.elsewhere.eyris.data.local.room.entity.BusinessEntity
import com.elsewhere.eyris.data.local.room.entity.ContactedBusinessEntity
import com.elsewhere.eyris.data.local.room.entity.UserEntity
import com.elsewhere.eyris.data.local.room.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {
    // Business operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(business: BusinessEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinesses(businesses: List<BusinessEntity>)

    @Update
    suspend fun updateBusiness(business: BusinessEntity)

    @Delete
    suspend fun deleteBusiness(business: BusinessEntity)

    @Query("SELECT * FROM businesses WHERE userId = :userId ORDER BY savedAt DESC")
    fun getBusinessesByUser(userId: String): Flow<List<BusinessEntity>>

    @Query("SELECT * FROM businesses WHERE id = :id")
    suspend fun getBusinessById(id: String): BusinessEntity?

    @Query("SELECT * FROM businesses WHERE userId = :userId AND name LIKE :query")
    fun searchBusinesses(userId: String, query: String): Flow<List<BusinessEntity>>

    @Query("DELETE FROM businesses WHERE userId = :userId AND id = :businessId")
    suspend fun deleteBusinessByIdAndUser(userId: String, businessId: String)

    @Query("SELECT * FROM businesses WHERE userId = :userId AND id NOT IN (SELECT businessId FROM contacted_businesses WHERE userId = :userId)")
    fun getLeads(userId: String): Flow<List<BusinessEntity>>

    // Contacted business operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactedBusiness(business: ContactedBusinessEntity)

    @Update
    suspend fun updateContactedBusiness(business: ContactedBusinessEntity)

    @Delete
    suspend fun deleteContactedBusiness(business: ContactedBusinessEntity)

    @Query("SELECT * FROM contacted_businesses WHERE userId = :userId ORDER BY contactedAt DESC")
    fun getContactedBusinessesByUser(userId: String): Flow<List<ContactedBusinessEntity>>

    @Query("SELECT * FROM contacted_businesses WHERE userId = :userId AND status = :status ORDER BY contactedAt DESC")
    fun getContactedBusinessesByStatus(userId: String, status: String): Flow<List<ContactedBusinessEntity>>

    @Query("SELECT * FROM contacted_businesses WHERE id = :id")
    suspend fun getContactedBusinessById(id: String): ContactedBusinessEntity?

    @Query("DELETE FROM contacted_businesses WHERE userId = :userId AND id = :contactedId")
    suspend fun deleteContactedBusinessByIdAndUser(userId: String, contactedId: String)

    // User operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)

    // Search history operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT 10")
    fun getSearchHistory(userId: String): Flow<List<SearchHistoryEntity>>

    @Query("DELETE FROM search_history WHERE userId = :userId")
    suspend fun clearSearchHistory(userId: String)

    // Sync operations
    @Query("SELECT * FROM businesses WHERE synced = 0")
    suspend fun getUnsyncedBusinesses(): List<BusinessEntity>

    @Query("SELECT * FROM contacted_businesses WHERE synced = 0")
    suspend fun getUnsyncedContactedBusinesses(): List<ContactedBusinessEntity>

    @Query("UPDATE businesses SET synced = 1 WHERE id = :id")
    suspend fun markBusinessSynced(id: String)

    @Query("UPDATE contacted_businesses SET synced = 1 WHERE id = :id")
    suspend fun markContactedBusinessSynced(id: String)
}
