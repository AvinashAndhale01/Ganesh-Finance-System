package com.finance.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.finance.entity.LoanApplication;
import com.finance.repository.LoanApplicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoanApplicationServiceTest {

	@Mock
	private LoanApplicationRepository loanAppRepo;

	@Mock
	private RestTemplate rT;

	@InjectMocks
	private LoanApplicationServiceImpl loanApplicationService;

	private LoanApplication loanApplication;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks
		loanApplication = new LoanApplication();
		loanApplication.setCustomerId(1L);
		loanApplication.setEmail("test@example.com");
		loanApplication.setStatus("Pending");
	}

	@Test
	public void testCreateApplication() {
		// Arrange
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setEmail("test@example.com");
		when(rT.getForObject(anyString(), eq(UserLoginDTO.class))).thenReturn(userLoginDTO);
		when(loanAppRepo.save(any(LoanApplication.class))).thenReturn(loanApplication);

		LoanApplication result = loanApplicationService.createApplication(loanApplication);

		assertNotNull(result);
		assertEquals("test@example.com", result.getEmail());
		verify(rT, times(1)).getForObject(anyString(), eq(UserLoginDTO.class));
		verify(loanAppRepo, times(1)).save(any(LoanApplication.class));
	}

	@Test
    public void testGetAllLoanApplication() {
        when(loanAppRepo.findAll()).thenReturn(Arrays.asList(loanApplication));

        
        List<LoanApplication> result = loanApplicationService.getAllLoanApplication();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanAppRepo, times(1)).findAll();
    }

	@Test
    public void testGetLoanApplicationById() {
        
        when(loanAppRepo.findById(1L)).thenReturn(Optional.of(loanApplication));

        
        LoanApplication result = loanApplicationService.getLoanApplicationById(1L);

        
        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        verify(loanAppRepo, times(1)).findById(1L);
    }

	@Test
    public void testUpdateStatus() {
        
        when(loanAppRepo.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanAppRepo.save(any(LoanApplication.class))).thenReturn(loanApplication);

        
        loanApplicationService.updateStatus("Approved", 1L);

        
        assertEquals("Approved", loanApplication.getStatus());
        verify(loanAppRepo, times(1)).findById(1L);
        verify(loanAppRepo, times(1)).save(any(LoanApplication.class));
    }

	@Test
    public void testGetAllByCustomerId() {
        
        when(loanAppRepo.findAllByCustomerId(1L)).thenReturn(Arrays.asList(loanApplication));

        
        List<LoanApplication> result = loanApplicationService.getAllByCustomerId(1L);

        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanAppRepo, times(1)).findAllByCustomerId(1L);
    }

	@Test
	public void testDeleteById() {

		doNothing().when(loanAppRepo).deleteById(1L);

		String result = loanApplicationService.deleteById(1L);

		assertEquals("application deleted", result);
		verify(loanAppRepo, times(1)).deleteById(1L);
	}
}
