package model

object ContractType {
  def all = List(Perm, Contract, Freelance)

  def fromInt(n: Int): Option[ContractType] = n match {
    case 1 => Some(Perm)
    case 2 => Some(Contract)
    case 3 => Some(Freelance)
    case _ => None
  }
}

sealed trait ContractType {
  val name: String
  val intValue: Int
}

case object Perm extends ContractType {
  val name = "permanent"
  val intValue = 1
}

case object Contract extends ContractType {
  val name = "contract"
  val intValue = 2
}

case object Freelance extends ContractType {
  val name = "freelance"
  val intValue = 3
}
