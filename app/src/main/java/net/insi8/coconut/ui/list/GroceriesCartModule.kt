package net.insi8.coconut.ui.list

import net.insi8.coconut.api.datasource.GroceriesCartDataSource
import net.insi8.coconut.api.datasource.GroceriesCartRepository
import net.insi8.coconut.cart.GroceriesCartUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groceriesCartModule = module {

    factory<GroceriesCartDataSource> {
        GroceriesCartRepository(coconutAPI = get())
    }

    factory<GroceriesCartUseCase> {
        GroceriesCartUseCase(dataSource = get(), cartDao = get())
    }

    viewModel {
        GroceriesCartViewModel(useCase = get())
    }
}