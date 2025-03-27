package com.ganesh.service;

import com.ganesh.entity.LoanApproval;
import com.ganesh.repository.LoanApprovalRepository;
import com.ganesh.service.LoanApplicationDTO;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanApprovalServiceTest {

    @Mock
    private LoanApprovalRepository loanApprovalRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoanApprovalService loanApprovalService;

    @Value("${minimum.credit.score}")
    private int minCreditScore = 650;

    private LoanApproval loanApproval;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        loanApproval = new LoanApproval();
        loanApproval.setLoanApplicationId(1L);
        loanApproval.setCreditScore(700);
        loanApproval.setIsApproved(false);
    }

    @Test
    public void testProcessLoanApproval_Approved() {
        // Arrange
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setCustomerId(101L);
        loanApplicationDTO.setLoanAmount(50000.0);
        loanApplicationDTO.setLoanType("Personal Loan");

        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(loanApplicationDTO);
        when(loanApprovalRepository.save(any(LoanApproval.class))).thenReturn(loanApproval);

        loanApproval.setCreditScore(700);  // Valid credit score for approval

        // Act
        LoanApproval result = loanApprovalService.processLoanApproval(loanApproval);

        // Assert
        assertTrue(result.getIsApproved());
        assertNull(result.getRejectionReason());
        assertEquals("Processed and Approved", loanApplicationDTO.getStatus());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(LoanApplicationDTO.class));
        verify(restTemplate, times(1)).put(anyString(), eq(loanApplicationDTO));
        verify(loanApprovalRepository, times(1)).save(any(LoanApproval.class));
    }

    @Test
    public void testProcessLoanApproval_Rejected() {
        // Arrange
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setCustomerId(101L);
        loanApplicationDTO.setLoanAmount(50000.0);
        loanApplicationDTO.setLoanType("Personal Loan");

        when(restTemplate.getForObject(anyString(), eq(LoanApplicationDTO.class))).thenReturn(loanApplicationDTO);
        when(loanApprovalRepository.save(any(LoanApproval.class))).thenReturn(loanApproval);

        loanApproval.setCreditScore(500);  // Low credit score for rejection

        // Act
        LoanApproval result = loanApprovalService.processLoanApproval(loanApproval);

        // Assert
        assertFalse(result.getIsApproved());
        assertEquals("Your Credit Score is Low", result.getRejectionReason());
        assertEquals("Processed and Rejected: Due to Low Credit Score", loanApplicationDTO.getStatus());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(LoanApplicationDTO.class));
        verify(restTemplate, times(1)).put(anyString(), eq(loanApplicationDTO));
        verify(loanApprovalRepository, times(1)).save(any(LoanApproval.class));
    }

    @Test
    public void testGetAllLoanApprovals() {
        // Arrange
        LoanApproval loanApproval1 = new LoanApproval();
        LoanApproval loanApproval2 = new LoanApproval();
        List<LoanApproval> loanApprovals = Arrays.asList(loanApproval1, loanApproval2);

        when(loanApprovalRepository.findAll()).thenReturn(loanApprovals);

        // Act
        List<LoanApproval> result = loanApprovalService.getAllLoanApprovals();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(loanApprovalRepository, times(1)).findAll();
    }

    @Test
    public void testGetLoanApprovalById() {
        // Arrange
        LoanApproval loanApproval = new LoanApproval();
        loanApproval.setId(1L);
        when(loanApprovalRepository.findById(1L)).thenReturn(java.util.Optional.of(loanApproval));

        // Act
        LoanApproval result = loanApprovalService.getLoanApprovalById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(loanApprovalRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetLoanApprovalById_NotFound() {
        // Arrange
        when(loanApprovalRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loanApprovalService.getLoanApprovalById(1L);
        });
        assertEquals("Loan Approval not found", exception.getMessage());
        verify(loanApprovalRepository, times(1)).findById(1L);
    }
}
