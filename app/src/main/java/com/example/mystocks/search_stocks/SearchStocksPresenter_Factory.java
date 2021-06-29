// Generated by Dagger (https://dagger.dev).
package com.example.mystocks.search_stocks;

import com.example.mystocks.SearchStocksInteractor;
import com.example.mystocks.StockInfoRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SearchStocksPresenter_Factory implements Factory<SearchStocksPresenter> {
  private final Provider<SearchStocksContractView> viewProvider;

  private final Provider<SearchStocksInteractor> interactorProvider;

  private final Provider<StockInfoRepository> repositoryProvider;

  public SearchStocksPresenter_Factory(Provider<SearchStocksContractView> viewProvider,
      Provider<SearchStocksInteractor> interactorProvider,
      Provider<StockInfoRepository> repositoryProvider) {
    this.viewProvider = viewProvider;
    this.interactorProvider = interactorProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchStocksPresenter get() {
    return newInstance(viewProvider.get(), interactorProvider.get(), repositoryProvider.get());
  }

  public static SearchStocksPresenter_Factory create(
      Provider<SearchStocksContractView> viewProvider,
      Provider<SearchStocksInteractor> interactorProvider,
      Provider<StockInfoRepository> repositoryProvider) {
    return new SearchStocksPresenter_Factory(viewProvider, interactorProvider, repositoryProvider);
  }

  public static SearchStocksPresenter newInstance(SearchStocksContractView view,
      SearchStocksInteractor interactor, StockInfoRepository repository) {
    return new SearchStocksPresenter(view, interactor, repository);
  }
}