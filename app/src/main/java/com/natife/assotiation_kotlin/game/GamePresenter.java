package com.natife.assotiation_kotlin.game;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.View mView;
    private GameContract.Repository mRepository;

    //передаем экземпляр View
    public GamePresenter(GameContract.View mView) {
        this.mView = mView;
        this.mRepository = new GameRepository();
    }

    @Override
    public void resultPressed() {

    }
}
