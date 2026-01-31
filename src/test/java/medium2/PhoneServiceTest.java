package medium2;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import medium2.model.PhoneRecord;
import medium2.repository.PhoneRepository;
import medium2.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @Mock
    private PhoneRepository repository;

    @InjectMocks
    private PhoneService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void testAddRecord_ValidName() {
        String name = "Alice";
        String phone = "123-456-7890";

        service.addRecord(name, phone);

        verify(repository, times(1)).save(any(PhoneRecord.class));
    }

    @Test
    void testAddRecord_NullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addRecord(null, "123");
        });
    }

    @Test
    void testAddRecord_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addRecord("   ", "123");
        });
    }

    @Test
    void testGetPhoneByName_Found() {
        String name = "Bob";
        PhoneRecord record = new PhoneRecord(name, "999-888-7777");
        when(repository.findByName(name)).thenReturn(Optional.of(record));

        String result = service.getPhoneByName(name);

        assertEquals("999-888-7777", result);
        verify(repository, times(1)).findByName(name);
    }

    @Test
    void testGetPhoneByName_NotFound() {
        String name = "Charlie";
        when(repository.findByName(name)).thenReturn(Optional.empty());

        String result = service.getPhoneByName(name);

        assertNull(result);
        verify(repository, times(1)).findByName(name);
    }

    @Test
    void testGetPhoneByName_NullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getPhoneByName(null);
        });
    }

    @Test
    void testGetPhoneByName_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getPhoneByName("   ");
        });
    }
}
