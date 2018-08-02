package com.natife.assotiation_kotlin.choose_how_play

class ChooseHowPlayPresenter(private val mView: ChooseHowPlayContract.View) : ChooseHowPlayContract.Presenter {
    private val mRepository: ChooseHowPlayContract.Repository

    init {
        this.mRepository = ChooseHowPlayRepository()
    }



    override fun initPlayerList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun btnAddPlayerClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}