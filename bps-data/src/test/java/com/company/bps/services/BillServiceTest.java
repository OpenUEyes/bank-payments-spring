package com.company.bps.services;

import com.company.bps.model.Bill;
import com.company.bps.repositories.BillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    BillRepository billRepository;

    @InjectMocks
    BillServiceImpl billService;

    @Test
    void findByIdAndAccountId() {
        when(billRepository.findByIdAndAccountId(anyLong(), anyLong())).thenReturn(Optional.of(new Bill()));

        billService.findByIdAndAccountId(1L, 1L);

        verify(billRepository, times(1)).findByIdAndAccountId(anyLong(), anyLong());

    }

    @Test
    void transferNotReturnErrorMessage() {
        final long senderId = 1;
        final long recipientId = 2;
        Bill sender = Bill.builder().id(senderId).balance(1000.0).build();
        Bill recipient = Bill.builder().id(recipientId).balance(1000.0).build();

        when(billRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(billRepository.findById(recipientId)).thenReturn(Optional.of(recipient));
        when(billRepository.save(any())).thenReturn(null);

        Optional<String> result = billService.transfer(senderId, recipientId, 200.0);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void transferReturnErrorMessageWhenSenderBalanceLowThenAmount() {
        final long senderId = 1;
        final long recipientId = 2;
        final double balance = 100.0;
        Bill sender = Bill.builder().id(senderId).balance(balance).build();
        Bill recipient = Bill.builder().id(recipientId).balance(balance).build();

        when(billRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(billRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        Optional<String> errorMessage = billService.transfer(senderId, recipientId, balance + 100);
        assertTrue(errorMessage.isPresent());
    }
}
