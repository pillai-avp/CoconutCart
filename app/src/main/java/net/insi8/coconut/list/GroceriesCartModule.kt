package net.insi8.coconut.list

import net.insi8.coconut.api.datasource.GroceriesCartDataSource
import net.insi8.coconut.api.datasource.GroceriesCartRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groceriesCartModule = module {

    factory<GroceriesCartDataSource> {
        GroceriesCartRepository(coconutAPI = get())
    }

    viewModel {
        GroceriesCartViewModel(cartDataSource = get())
    }
}