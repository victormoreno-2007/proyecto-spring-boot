# realizacion del examen 

en el proceso de esta elaboración se editaron varios archivos para obtener el resultado pedido y esperado.

### paymentRepositoryPort
se modifica para crear el contrato que va a decolver una lista de typo Payment

### BookingController
se modica para la autenticacion 

### PaymentsController
se crea el metodo get con la ruta, este no se crea como dice en el examen si no que se coloca my-payments para no trener la necesidad de obtener el id y tenerlo pendiente en esta, se hace solo

### paymentRepositoryAdapter
se sobrescribe el contrato creado en PaymentRepositoryPort

### jwtTokenAdapter
se implementa dos metodos para validar el token y obtener el usuario de ese token, esta es la magia que hace que no se necesite colocar en el metodo get el {id}, nos evitamos esto

### securityConfig
se implementa la url para que se encesite estar autenticado

### SecurityUser
se crea el metodo para obtener el id del cliente
