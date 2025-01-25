package com.project.cointraker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.cointraker.db.dao.InterestCoinDAO
import com.project.cointraker.db.entity.InterestCoinEntity

// https://developer.android.com/codelabs/basic-android-kotlin-training-persisting-data-room?hl=ko#0
@Database(entities = [InterestCoinEntity::class], version = 1)
abstract class CoinPriceDatabase : RoomDatabase() { // RoomDatabase를 확장하는 추상 클래스

    // 데이버베이스는 반환하는 DAO가 있어야 하고 여러개도 가능
    abstract fun interestCoinDAO()  : InterestCoinDAO

    // companion 객체를 통해 이름을 한정자로 사용하여 데이터베이스를 만들거나 가져오는 메서드에 접근 가능
    companion object {

        // @Volatile에 INSTANCE 주석 달기 -> 인스턴스 값이 항상 최신 상태 유지
        @Volatile
        private var INSTANCE : CoinPriceDatabase? = null

        // 빌더에 필요한 Context 매개변수 사용해 메서드 정의
        fun getDatabase (context: Context) : CoinPriceDatabase {
            // 코드를 래핑하여 synchronized 블록 내에서 데이터 베이스를 가져와야
            // 한번에 한 실행 스레드만 들어와 한번만 초기화됨
            // 인스턴스를 반환하거나 없으면 synchronized 블록내에서 초기화
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDatabase::class.java,
                    "coin_database"
                )
                    // 이전 스키마의 모든 행을 가져와 새 스키마의 행으로 변환하는 방법을 정의하는 객체
                    .fallbackToDestructiveMigration()
                    .build()
                // sychronized 블록 내에서 INSTANCE = instance 할당
                INSTANCE = instance
                // 인스턴스 반환
                instance
            }
        }


    }
}