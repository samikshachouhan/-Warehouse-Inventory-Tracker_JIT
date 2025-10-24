public class Product {
        private final String id;
        private final String name;
        private int quantity;
        private final int reorderThreshold;

        public Product(String id, String name, int quantity, int reorderThreshold) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.reorderThreshold = reorderThreshold;
        }

        // Getters
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public int getQuantity() {
            return quantity;
        }
        public int getReorderThreshold() {
            return reorderThreshold;
        }

        // Setters
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public void increaseQuantity(int amount) {
            if(amount > 0) {
                quantity += amount;
            }
        }
        
        public void decreaseQuantity(int amount) throws IllegalAccessException  {
            if(amount > 0 && amount <= quantity) {
                quantity -= amount;
            } else {
                throw new IllegalAccessException("Insufficient stock for " +name);
            }
        }

        @Override
        public String toString() {
            return "Product{" + "ID='" +id+ '\'' + ", Name='" +name+ '\'' + ", Quantity=" +quantity+ ", Threshold=" +reorderThreshold+ '}';
        }
        
}
