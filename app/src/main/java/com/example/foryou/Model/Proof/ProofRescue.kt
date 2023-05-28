package com.example.foryou.Model.Proof

data class ProofRescue(val code: Int = 0,
                       val data: DataProof,
                       val success: Boolean = false,
                       val timestamp: Long = 0)
data class DataProof(val proofs: List<ProofsItem>?)
data class ProofsItem(val createdAt: String = "",
                      val imageUrl: String = "",
                      val id: String = "")