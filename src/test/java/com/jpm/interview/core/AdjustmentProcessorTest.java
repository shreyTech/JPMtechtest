package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.Adjustment;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdjustmentProcessorTest {

    @Mock
    private ReportLogger reportLogger;

    @Mock
    private SaleDAO mockedSaleDAO;

    private AdjustmentProcessor mockedMessageProcessor;
    private SaleValidator validator;
    private Integer saleReportInterval = 10;
    private Integer maxMessages = 20;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        validator = new SaleValidator();
        mockedMessageProcessor = new AdjustmentProcessor(mockedSaleDAO, reportLogger, saleReportInterval,
            maxMessages, validator);
    }

    @Test
    public void should_process_sale_and_addAdjustments_successfully() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.ADD);
        adjustment.setValue(10);

        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(1);

        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verify(mockedSaleDAO).addAdjustment(productName, 10);
        verifyZeroInteractions(reportLogger);
    }

    @Test
    public void should_process_sale_and_substractAdjustments_successfully() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.SUBTRACT);
        adjustment.setValue(10);

        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(1);

        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verify(mockedSaleDAO).subtractAdjustment(productName, 10);
        verifyZeroInteractions(reportLogger);
    }

    @Test
    public void should_process_sale_and_multiplyAdjustments_successfully() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.MULTIPLY);
        adjustment.setValue(10);

        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(1);

        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verify(mockedSaleDAO).multiplyAdjustment(productName, 10);
        verifyZeroInteractions(reportLogger);
    }


}