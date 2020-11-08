package com.progwml6.natura.overworld.block;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum LogType implements IStringSerializable {
  MAPLE,
  SILVERBELL,
  AMARANTH,
  TIGER,
  WILLOW,
  EUCALYPTUS,
  HOPSEED,
  SAKURA;

  private final String name;

  LogType() {
    this.name = this.name().toLowerCase(Locale.US);
  }

  @Override
  public String getString() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
