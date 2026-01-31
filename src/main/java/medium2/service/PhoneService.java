package medium2.service;

import medium2.model.PhoneRecord;
import medium2.repository.PhoneRepository;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    private final PhoneRepository repository;

    public PhoneService(PhoneRepository repository) {
        this.repository = repository;
    }

    public void addRecord(String name, String phone) {
        validateName(name);
        PhoneRecord record = new PhoneRecord(name, phone);
        repository.save(record);
    }

    public String getPhoneByName(String name) {
        validateName(name);
        return repository.findByName(name)
                .map(PhoneRecord::getPhone)
                .orElse(null);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым.");
        }
    }
}
