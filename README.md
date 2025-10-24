# -Warehouse-Inventory-Tracker_JIT
Design and implement a Java application to simulate an event-based warehouse inventory tracker.
# Warehouse Inventory Tracker (Event-Based)

## 📖 Overview
A Simple **Java application** to simulate an event-driven warehouse inventory tracker using the **Observe Design Pattern**.

## 💡Features
This document contains a step-by-step design, full Java source code, run instructions, and Git commands to create a GitHub repository. Use it as a ready-made project you can copy into files and push to GitHub. Overview

A lightweight in-memory Warehouse Inventory Tracker that:

Maintains a catalog of products (ID, name, quantity, reorder threshold).

Receives shipments (increase stock) and fulfills orders (decrease stock).

Automatically triggers "Restock Alert" when a product's quantity falls below its threshold, via an observer/alert service.

Supports adding products dynamically.

Handles invalid IDs and insufficient stock gracefully. Bonus: thread-safe operations, file persistence (CSV), and support for multiple warehouses.

Design / Architecture Product — model for an item (id, name, quantity, threshold).

AlertService — observer interface triggered when stock is low.

ConsoleAlertService — simple implementation that prints messages.

Warehouse — manages a Map<Integer, Product> and a list of AlertService observers; methods: addProduct, receiveShipment, fulfillOrder, getProduct, listProducts.

WarehouseManager — optional: manage multiple warehouses by name/ID.

InventoryPersistence — save/load inventory to/from a CSV file.

Main — demo + example workflow and optional multithreaded simulation. package com.example.warehouse;

product.java public class Product { private final int id; private String name; private int quantity; private int reorderThreshold;

public Product(int id, String name, int quantity, int reorderThreshold) { this.id = id; this.name = name; this.quantity = quantity; this.reorderThreshold = reorderThreshold; }

public int getId() { return id; } public String getName() { return name; } public synchronized int getQuantity() { return quantity; } public synchronized int getReorderThreshold() { return reorderThreshold; }

public synchronized void increase(int amount) { if (amount < 0) throw new IllegalArgumentException("Amount must be >= 0"); this.quantity += amount; }

public synchronized boolean decrease(int amount) { if (amount < 0) throw new IllegalArgumentException("Amount must be >= 0"); if (amount > this.quantity) return false; this.quantity -= amount; return true; }

public synchronized String toCSV() { return id + "," + escape(name) + "," + quantity + "," + reorderThreshold; }

public static Product fromCSV(String line) { String[] parts = line.split(",", 4); if (parts.length < 4) throw new IllegalArgumentException("Bad CSV: " + line); int id = Integer.parseInt(parts[0]); String name = unescape(parts[1]); int qty = Integer.parseInt(parts[2]); int thr = Integer.parseInt(parts[3]); return new Product(id, name, qty, thr); }

private static String escape(String s) { return s.replace("\", "\\").replace(",", "\,"); } private static String unescape(String s) { return s.replace("\,", ",").replace("\\", "\"); }

AlterService.java

@Override public String toString() { return "Product{" + "id=" + id + ", name='" + name + ''' + ", qty=" + quantity + ", thr=" + reorderThreshold + '}'; }

ConsoleAlertService.java public class ConsoleAlertService implements AlertService { @Override public void onLowStock(Product product) { System.out.println("[ALERT] Low stock for " + product.getName() + " — only " + product.getQuantity() + " left!"); } }
