package ru.alexey.springmvccrud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexey.springmvccrud.models.Item;
import ru.alexey.springmvccrud.models.Person;
import ru.alexey.springmvccrud.repositories.ItemsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemsService {

    private ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(Person owner) {
        return itemsRepository.findByOwner(owner);
    }
}
