# inventory management system

Restful web service to manage products.
Allows to create, update and delete products as well as get list of products with quantity less than 5 or list of products by specific parameter(name or brand).

H2 database is used to store data.

_There are two ways to run the application:_
1) From command line. 
Maven and jre 1.8 or higher must be installed. 
    
     `mvn spring-boot:run`
2) By using docker.

    From project root directory run next commands:

        `docker image build -t inventory-management .`

        `docker container run -p 8080:8080 inventory-management`

###### **Available endpoints:**

There are 2 available roles: 

    - USER(user/user)  - only read 
    
    - ADMIN(admin/admin) - reade/write
    
create new product:

`curl -X POST -u admin:admin localhost:8080/products -H 'Content-type:application/json' -d '{"name": "watch","brand": "casio","price": 50.50,"quantity": 5}'`

update product: 

`curl -X PUT -u admin:admin localhost:8080/products -H 'Content-type:application/json' -d '{"id": 5,"name": "watch","brand": "swatch","price": 50.50,"quantity": 5}'`

delete product by id:

`curl -X DELETE -u admin:admin localhost:8080/products/1`

get list of leftovers:

`curl -u user:user localhost:8080/products/leftovers`

get list of products by name(optional) and brand(optional):

`curl -u user:user localhost:8080/products?name=watch&brand=casio`
