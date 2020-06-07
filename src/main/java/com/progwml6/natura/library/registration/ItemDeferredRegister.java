package com.progwml6.natura.library.registration;

import com.progwml6.natura.library.registration.object.EnumObject;
import com.progwml6.natura.library.registration.object.ItemObject;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.item.TooltipItem;

import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemDeferredRegister extends RegisterWrapper<Item> {

  public ItemDeferredRegister(String modID) {
    super(ForgeRegistries.ITEMS, modID);
  }

  /**
   * Initializes this registry wrapper. Needs to be called during mod construction
   */
  @Override
  public void register(IEventBus bus) {
    register.register(bus);
  }

  /**
   * Adds a new supplier to the list to be registered, using the given supplier
   * @param name   Item name
   * @param sup    Supplier returning an item
   * @return Item registry object
   */
  public <I extends Item> ItemObject<I> register(final String name, final Supplier<? extends I> sup) {
    return new ItemObject<>(register.register(name, sup));
  }

  /**
   * Adds a new supplier to the list to be registered, based on the given item properties
   * @param name   Item name
   * @param props  Item properties
   * @return Item registry object
   */
  public ItemObject<Item> register(final String name, Item.Properties props) {
    return register(name, () -> new TooltipItem(props));
  }

  /* Specialty */

  /**
   * Registers a block with slab, stairs, and walls
   * @param name      Name of the block
   * @param supplier  Function to get a item for the given enum value
   * @return EnumObject mapping between different item types
   */
  @SuppressWarnings("unchecked")
  public <T extends Enum<T> & IStringSerializable, I extends Item> EnumObject<T, I> registerEnum(final T[] values, final String name, Function<T, ? extends I> supplier) {
    if (values.length == 0) {
      throw new IllegalArgumentException("Must have at least one value");
    }
    // note this cast only works because you cannot extend an enum
    EnumMap<T, Supplier<? extends I>> map = new EnumMap<>((Class<T>) values[0].getClass());
    for (T value : values) {
      map.put(value, this.register(value.getName() + "_" + name, () -> supplier.apply(value)));
    }
    return new EnumObject<>(map);
  }
}
