package dev.osmanb.portfolioService.impl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import dev.osmanb.portfolioService.service.impl.MarkowitzModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import dev.osmanb.portfolioService.model.*;

class MarkowitzModelTest {

    @Mock
    private YFinanceResponse yFinanceResponse;

    @InjectMocks
    private MarkowitzModel markowitzModel;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOptimalPortfolio() {
        // Arrange Mocks
        when(yFinanceResponse.getRiskFreeRate()).thenReturn(0.01);
        when(yFinanceResponse.getExpectedReturns()).thenReturn(new double[]{0.03, 0.04, 0.05});
        when(yFinanceResponse.getTickers()).thenReturn(Arrays.asList("A", "B", "C"));
        when(yFinanceResponse.getCovarianceMatrix()).thenReturn(new double[][]{
                {0.04, 0.03, 0.05},
                {0.03, 0.09, 0.09},
                {0.05, 0.09, 0.16}
        });

        // Act
        PortfolioResponse portfolioResponse = markowitzModel.getOptimalPortfolio(yFinanceResponse);

        // Assert
        assertNotNull(portfolioResponse);
        assertEquals(0.5555555555555552,portfolioResponse.getOptimalPortfolio().getWeights()[0]);
        assertEquals(0.3333333333333333,portfolioResponse.getOptimalPortfolio().getWeights()[1]);
        assertEquals(0.11111111111111138,portfolioResponse.getOptimalPortfolio().getWeights()[2]);
        assertEquals(0.03555555555555556,portfolioResponse.getOptimalPortfolio().getExpectedReturn());
        assertEquals(0.21970799925872436,portfolioResponse.getOptimalPortfolio().getStdDeviation());

        assertEquals(3,portfolioResponse.getTickers().size());

        assertEquals(0.03,portfolioResponse.getTickers().get(0).getExpectedReturn());
        assertEquals(0.04,portfolioResponse.getTickers().get(1).getExpectedReturn());
        assertEquals(0.05,portfolioResponse.getTickers().get(2).getExpectedReturn());

        assertEquals(Math.sqrt(0.04),portfolioResponse.getTickers().get(0).getStdDeviation());
        assertEquals(Math.sqrt(0.09),portfolioResponse.getTickers().get(1).getStdDeviation());
        assertEquals(Math.sqrt(0.16),portfolioResponse.getTickers().get(2).getStdDeviation());
    }
}