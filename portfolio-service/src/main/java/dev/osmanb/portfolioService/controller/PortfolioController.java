package dev.osmanb.portfolioService.controller;


import dev.osmanb.portfolioService.model.PortfolioRequest;
import dev.osmanb.portfolioService.model.PortfolioResponse;
import dev.osmanb.portfolioService.model.YFinanceResponse;
import dev.osmanb.portfolioService.service.PortfolioModel;
import dev.osmanb.portfolioService.service.PortfolioOptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/portfolio/optimization")
@CrossOrigin
public class PortfolioController {
    
    private final PortfolioOptimizationService portfolioOptimizationService;
    private final PortfolioModel<YFinanceResponse, PortfolioResponse> portfolioModel;

    public PortfolioController(PortfolioOptimizationService portfolioOptimizationService, PortfolioModel<YFinanceResponse, PortfolioResponse> portfolioModel) {
        this.portfolioOptimizationService = portfolioOptimizationService;
        this.portfolioModel = portfolioModel;
    }

    @PostMapping
    public ResponseEntity<PortfolioResponse> getMarkowitzPortfolio(@RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioOptimizationService.optimalPortfolio(request));
    }
}
