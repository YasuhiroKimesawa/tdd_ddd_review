package zozo.infrastructure; /*
                              * Copyright 2022 Junichi Kato
                              *
                              * Licensed under the Apache License, Version 2.0 (the "License");
                              * you may not use this file except in compliance with the License.
                              * You may obtain a copy of the License at
                              *
                              *     http://www.apache.org/licenses/LICENSE-2.0
                              *
                              * Unless required by applicable law or agreed to in writing, software
                              * distributed under the License is distributed on an "AS IS" BASIS,
                              * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                              * See the License for the specific language governing permissions and
                              * limitations under the License.
                              */

import java.util.Objects;

public class CartItemRecord {
  private String id;

  private String cartId;

  private String itemName;

  private int quantity;

  private int price;

  public CartItemRecord(String id, String cartId, String itemName, int quantity, int price) {
    this.id = id;
    this.cartId = cartId;
    this.itemName = itemName;
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItemRecord that = (CartItemRecord) o;
    return quantity == that.quantity
        && price == that.price
        && id.equals(that.id)
        && cartId.equals(that.cartId)
        && itemName.equals(that.itemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cartId, itemName, quantity, price);
  }

  public String getId() {
    return id;
  }

  public String getCartId() {
    return cartId;
  }

  public String getItemName() {
    return itemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getPrice() {
    return price;
  }
}
