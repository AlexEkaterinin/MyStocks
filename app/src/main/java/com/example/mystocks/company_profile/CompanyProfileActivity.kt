package com.example.mystocks.company_profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mystocks.databinding.CompanyProfileScreenBinding
import com.example.mystocks.isVisible
import com.example.mystocks.model.CompanyProfileModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CompanyProfileActivity : AppCompatActivity(), CompanyProfileScreenContract.View {

    private lateinit var binding: CompanyProfileScreenBinding

    @Inject
    lateinit var presenter: CompanyProfileScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompanyProfileScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val stockSymbol: String? = intent.getStringExtra("symbol")

        binding.toolbar.run {
            setNavigationOnClickListener {
                finish()
                true
            }
        }

        with(binding.favoriteBtn) {
            isSelected = intent.getBooleanExtra("isFavorite", false)
            setOnClickListener { favoriteBtn ->
                if(isSelected){
                    presenter.removeFavoriteStock(stockSymbol)
                } else {
                    presenter.inserFavoriteStock(stockSymbol)
                }
                favoriteBtn.isSelected = !isSelected
            }
        }

        binding.companyName.text = intent.getStringExtra("companyName")
        binding.companySymbol.text = stockSymbol

        presenter.getCompanyProfileInfo(stockSymbol)
        showProgress(true)
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showCompanyInfo(companyInfo: CompanyProfileModel) {
        binding.addressText.text = companyInfo.address
        binding.cityText.text = companyInfo.city
        binding.countryText.text = companyInfo.country
        binding.industryText.text = companyInfo.industry
        binding.sectorText.text = companyInfo.sector
        binding.employeesText.text = companyInfo.fullTimeEmployees.toString()
        binding.websiteText.text = companyInfo.website
        binding.summaryText.text = companyInfo.longBusinessSummary
    }

    override fun showProgress(show: Boolean) {
        binding.progressBar.isVisible(show)
        binding.addressCaption.isVisible(!show)
        binding.cityCaption.isVisible(!show)
        binding.countryCaption.isVisible(!show)
        binding.industryCaption.isVisible(!show)
        binding.sectorCaption.isVisible(!show)
        binding.employeesCaption.isVisible(!show)
        binding.websiteCaption.isVisible(!show)
        binding.summaryCaption.isVisible(!show)
    }


    override fun showError() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
    }
}
