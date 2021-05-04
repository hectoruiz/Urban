package com.urban.androidhomework

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.urban.androidhomework.BundleConstants.NAME
import com.urban.androidhomework.Utils.Companion.getName
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_character_empty.*
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val charactersAdapter by lazy {
        CharactersAdapter()
    }

    @Inject
    lateinit var networkApi: NetworkApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecyclerView()
        getAllCharacters()
    }

    private fun getAllCharacters() {
        hideEmptyView()
        showProgressBar()
        networkApi
                .allCharacters
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : SingleObserver<Response<Character>> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onSuccess(characterResponse: Response<Character>) {
                                onSuccessResult(characterResponse)
                                hideProgressBar()
                            }

                            override fun onError(e: Throwable) {
                                hideProgressBar()
                                showEmptyView()
                            }
                        })
    }

    private fun showProgressBar() {
        characters_progress_bar.show()
    }

    private fun hideProgressBar() {
        characters_progress_bar.hide()
    }

    private fun initializeRecyclerView() {
        characters_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(CharacterItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
            adapter = charactersAdapter
        }
    }

    private fun onSuccessResult(characterResponse: Response<Character>) {
        characterResponse.body()?.let {
            hideEmptyView()
            charactersAdapter.setCharacters(getName(characterResponse))
            charactersAdapter.onItemClick = { character ->
                val characterFragment = CharacterFragment()
                val bundle = Bundle()
                bundle.putString(NAME, character)
                characterFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.main_container, characterFragment).commit()
            }
        } ?: showEmptyView()
    }

    private fun showEmptyView() {
        empty_view.visibility = View.VISIBLE
        retry_button.setOnClickListener {
            getAllCharacters()
        }
    }

    private fun hideEmptyView() {
        empty_view.visibility = View.GONE
    }
}