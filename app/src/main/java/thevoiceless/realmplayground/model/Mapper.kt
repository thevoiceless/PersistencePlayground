package thevoiceless.realmplayground.model

interface Mapper<NetworkModel, PersistenceModel> {
    fun toNetworkModel(persistenceModel: PersistenceModel): NetworkModel
    fun toPersistenceModel(networkModel: NetworkModel): PersistenceModel
}
