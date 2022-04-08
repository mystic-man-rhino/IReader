package org.ireader.presentation.feature_services.updater_service

import org.ireader.core.utils.Constants.github_api_url
import org.ireader.core.utils.Constants.repo_url
import org.ireader.core.utils.RetrofitProvider
import org.ireader.presentation.feature_services.updater_service.models.Release
import retrofit2.http.GET
import javax.inject.Inject

class UpdateApi @Inject constructor(
    retrofitProvider: RetrofitProvider,
) {

    private val api = retrofitProvider
        .get(github_api_url)
        .create(GithubApi::class.java)

    suspend fun checkRelease(): Release {
        return api.checkRelease()
    }

    private interface GithubApi {
        @GET(repo_url)
        suspend fun checkRelease(): Release
    }
}