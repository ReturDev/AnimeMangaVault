package github.returdev.animemangavault.ui.screen.showmore

sealed class ShowMoreUiAction{

    object GetTopAnime : ShowMoreUiAction()

    object GetTopManga : ShowMoreUiAction()

    object GetThisSeason : ShowMoreUiAction()

}
