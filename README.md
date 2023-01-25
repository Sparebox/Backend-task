# Backend-task

A submission for the Weasel Software backend task.

The main functionality is divided into the `ComponentProducer` and `Factory` classes. 

- The `ComponentProducer` class produces one type of component defined by the `Component` enum for a destination **factory**.
The producer can be customized with the units per hour, packaging size, and hours to deliver variables.

- The `Delivery` class represents one unit of delivery.

- The `Factory` class takes in components through **deliveries** from component producers. Once there are enough components for a given **recipe** the factory makes products
at the defined production rate.

- The **product recipe** can be customized by adding individual components and their required amounts with the `ProductRecipe` class.

The simulation runs in ticks so that one tick equals one simulation hour.
