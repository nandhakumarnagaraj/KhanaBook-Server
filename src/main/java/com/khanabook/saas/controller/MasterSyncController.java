package com.khanabook.saas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khanabook.saas.entity.*;
import com.khanabook.saas.security.TenantContext;
import com.khanabook.saas.service.*;

import lombok.RequiredArgsConstructor;
import lombok.Data;
import java.util.List;

@RestController
@RequestMapping("/sync/master")
@RequiredArgsConstructor
public class MasterSyncController {

  private final RestaurantProfileService restaurantProfileService;
  private final UserService userService;
  private final CategoryService categoryService;
  private final MenuItemService menuItemService;
  private final ItemVariantService itemVariantService;
  private final RawMaterialService rawMaterialService;
  private final MaterialBatchService materialBatchService;
  private final RecipeIngredientService recipeIngredientService;
  private final StockLogService stockLogService;
  private final RawMaterialStockLogService rawMaterialStockLogService;
  private final BillService billService;
  private final BillItemService billItemService;
  private final BillPaymentService billPaymentService;

  @GetMapping("/pull")
  public ResponseEntity<MasterSyncResponse> pullMasterSync(
      @RequestParam Long lastSyncTimestamp,
      @RequestParam String deviceId,
      @RequestParam(required = false, defaultValue = "500") Integer limit) {

    Long tenantId = TenantContext.getCurrentTenant();

    MasterSyncResponse response = new MasterSyncResponse();
    response.setProfiles(restaurantProfileService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setUsers(userService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setCategories(categoryService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setMenuItems(menuItemService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setItemVariants(itemVariantService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setRawMaterials(rawMaterialService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setMaterialBatches(materialBatchService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setRecipeIngredients(recipeIngredientService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setStockLogs(stockLogService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setRawMaterialStockLogs(rawMaterialStockLogService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setBills(billService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setBillItems(billItemService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));
    response.setBillPayments(billPaymentService.pullData(tenantId, lastSyncTimestamp, deviceId, limit));

    return ResponseEntity.ok(response);
  }

  @Data
  public static class MasterSyncResponse {
    private List<RestaurantProfile> profiles;
    private List<User> users;
    private List<Category> categories;
    private List<MenuItem> menuItems;
    private List<ItemVariant> itemVariants;
    private List<RawMaterial> rawMaterials;
    private List<MaterialBatch> materialBatches;
    private List<RecipeIngredient> recipeIngredients;
    private List<StockLog> stockLogs;
    private List<RawMaterialStockLog> rawMaterialStockLogs;
    private List<Bill> bills;
    private List<BillItem> billItems;
    private List<BillPayment> billPayments;
  }
}
