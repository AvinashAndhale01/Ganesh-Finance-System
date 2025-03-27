package com.finance.service;

import com.finance.entity.Payment;
import com.finance.repository.PaymentServiceRepository;
import com.finance.service.LoanApplicationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentServiceRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;

    private LoanApplicationDTO loanApplicationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        // Create a sample Payment
        payment = new Payment();
        payment.setLoanId(1L);
        payment.setAmountPaid(2000.0);

        // Create a sample LoanApplicationDTO
        loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setId(1L);
        loanApplicationDTO.setLoanAmount(5000.0);
        loanApplicationDTO.setStatus("Open");
    }

    @Test
    void testProcessPayment_Success() {
        
        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(loanApplicationDTO);
        when(paymentRepository.findByLoanId(anyLong())).thenReturn(Arrays.asList(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        double remainingAmount = loanApplicationDTO.getLoanAmount() - payment.getAmountPaid();
        payment.setDueAmount(remainingAmount);

        
        Payment processedPayment = paymentService.processPayment(payment);

        assertNotNull(processedPayment);
        assertEquals(remainingAmount, processedPayment.getDueAmount());
        assertEquals(LocalDate.now(), processedPayment.getPaymentDate());

        verify(paymentRepository, times(1)).save(payment);
        verify(restTemplate, times(1)).put(anyString(), eq(loanApplicationDTO));
    }

    @Test
    void testProcessPayment_LoanNotFound() {
     
        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.processPayment(payment);
        });
        assertEquals("Loan Application not found for id:" + payment.getLoanId(), exception.getMessage());
    }

    @Test
    void testProcessPayment_LoanClosed() {
        // Arrange
        loanApplicationDTO.setStatus("Closed");
        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(loanApplicationDTO);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.processPayment(payment);
        });
        assertEquals("Loan is already closed for Application id:" + payment.getLoanId(), exception.getMessage());
    }

    @Test
    void testProcessPayment_ExceedsRemainingAmount() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(loanApplicationDTO);
        when(paymentRepository.findByLoanId(anyLong())).thenReturn(Arrays.asList(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        double remainingAmount = loanApplicationDTO.getLoanAmount() - payment.getAmountPaid();
        payment.setDueAmount(remainingAmount);

        // Set the amount paid to exceed remaining loan amount
        payment.setAmountPaid(6000.0);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.processPayment(payment);
        });
        assertEquals("Payment exceeds the remaining loan amount. Please pay only: " + remainingAmount, exception.getMessage());
    }

    @Test
    void testGetPaymentById() {
        // Arrange
        when(paymentRepository.findById(anyLong())).thenReturn(java.util.Optional.of(payment));

        // Act
        Payment result = paymentService.getPaymentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(payment, result);
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPaymentById_NotFound() {
        // Arrange
        when(paymentRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(1L);
        });
        assertEquals("Payment not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllPayments() {
        // Arrange
        List<Payment> payments = Arrays.asList(payment, new Payment());
        when(paymentRepository.findAll()).thenReturn(payments);

        // Act
        List<Payment> result = paymentService.getAllPayments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetPaymentsByLoanId() {
        // Arrange
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findByLoanId(anyLong())).thenReturn(payments);

        // Act
        List<Payment> result = paymentService.getPaymentsByLoanId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository, times(1)).findByLoanId(1L);
    }
}
