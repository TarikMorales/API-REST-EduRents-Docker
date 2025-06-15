package com.ingsoft.tf.api_edurents.repository.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUsuario(User usuario);
    Optional<Transaction> findByProductoId(Integer idProducto);
    List<Transaction> findByUsuarioAndEstado(User usuario, TransactionStatus estado);
    Optional<Transaction> findByProductoIdAndUsuarioId(Integer idProducto, Integer idUsuario);
    boolean existsByProductoIdAndUsuarioId(Integer idProducto, Integer idUsuario);
    List<Transaction> findByProductoIdAndProductoVendedorId(Integer idProduct, Integer idSeller);


}
