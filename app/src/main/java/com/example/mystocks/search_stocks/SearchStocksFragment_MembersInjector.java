// Generated by Dagger (https://dagger.dev).
package com.example.mystocks.search_stocks;

import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SearchStocksFragment_MembersInjector implements MembersInjector<SearchStocksFragment> {
  private final Provider<SearchStocksContractPresenter> presenterProvider;

  public SearchStocksFragment_MembersInjector(
      Provider<SearchStocksContractPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<SearchStocksFragment> create(
      Provider<SearchStocksContractPresenter> presenterProvider) {
    return new SearchStocksFragment_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(SearchStocksFragment instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  @InjectedFieldSignature("com.example.mystocks.search_stocks.SearchStocksFragment.presenter")
  public static void injectPresenter(SearchStocksFragment instance,
      SearchStocksContractPresenter presenter) {
    instance.presenter = presenter;
  }
}
