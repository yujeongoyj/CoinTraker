package com.project.cointraker

import com.project.cointraker.network.model.CurrentPriceList
import retrofit2.http.GET

interface Api {

    // http 메서드는 Retrofit의 어노테이션을 사용해서 정의하고
    // Retorfit의 BASE_URL과 결합되어 최종 URL이 만들어짐
    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentConList() : CurrentPriceList // suspend는 코루틴 내에서 실행되는 비동기 처리가 가능한 함수


}