package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.MenuItem;
import com.sskim.eatgo.domain.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository){
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenuItems(Long restaurantId) {
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(restaurantId);

        return Optional.ofNullable(menuItems).orElse(Collections.emptyList());
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for (MenuItem item : menuItems) {
            update(restaurantId, item);
        }
    }

    private void update(Long restaurantId, MenuItem item) {
        if(item.isDestroy()){
            menuItemRepository.deleteById(item.getId());
            return;
        }
        item.setRestaurantId(restaurantId);
        menuItemRepository.save(item);
    }
}