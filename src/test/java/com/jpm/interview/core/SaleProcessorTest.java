package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
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
public class SaleProcessorTest {

    @Mock
    private ReportLogger reportLogger;

    @Mock
    private SaleDAO mockedSaleDAO;

    private SaleProcessor mockedMessageProcessor;
    private SaleValidator validator;
    private Integer saleReportInterval = 10;
    private Integer maxMessages = 50;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        validator = new SaleValidator();
        mockedMessageProcessor = new SaleProcessor(mockedSaleDAO, reportLogger, saleReportInterval, maxMessages, validator);
    }

    @Test
    public void should_throwException_when_mandatoryParameters_are_missing() {

         expectedException.expect(RuntimeException.class);
         expectedException.expectMessage("Invalid sale message, missing mandatory parameters");

         mockedMessageProcessor.process(new SaleNotification());

    }

    @Test
    public void should_process_validSale_successfully_and_persistMessage_in_salesDB() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(1);

        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verifyZeroInteractions(reportLogger);
    }

    @Test
    public void should_generate_salesReport_after_saleReportInterval_is_reached() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(saleReportInterval);
        when(reportLogger.generateSalesReport()).thenReturn("");
        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verify(reportLogger).generateSalesReport();
    }

    @Test
    public void should_generate_adjustmentReport_after_adjustmentReportInterval_is_reached() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);

        when(mockedSaleDAO.getTotalNumberOfSales()).thenReturn(maxMessages);
        when(reportLogger.generateSalesReport()).thenReturn("");
        when(reportLogger.generateAdjustmentReport()).thenReturn("");
        mockedMessageProcessor.process(anAppleSale);

        verify(mockedSaleDAO).persistSale(anAppleSale);
        verify(reportLogger).generateSalesReport();
        verify(reportLogger).generateAdjustmentReport();
    }


}