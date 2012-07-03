package com.richardpianka.chess.common

import com.richardpianka.chess.game.{Coordinate, Board}
import com.richardpianka.chess.network.Contracts.Version
import org.junit.{Before, Test}
import java.io.File

class WarehouseTests {
  @Before
  def Init() {
    Utilities.delete(new File("temp/"))
    new File("temp/").mkdir()
  }

  @Test
  def GetSet() {
    val warehouse = new KeyedWarehouse[Int, Version]("temp/version.dat", _.getVersionMajor, Version.parseDelimitedFrom(_))
    val version = Version.newBuilder.setVersionMajor(1)
                                    .setVersionMinor(2)
                                    .setVersionRevision(3).build

    warehouse.add(version, false)

    val retrieved = warehouse(1)

    assert(warehouse.all.size == 1)
    assert(retrieved.getVersionMajor == version.getVersionMajor &&
           retrieved.getVersionMinor == version.getVersionMinor &&
           retrieved.getVersionRevision == version.getVersionRevision)
  }

  @Test
  def SaveLoadMultiple() {
    val warehouseA = new KeyedWarehouse[Int, Version]("temp/version.dat", _.getVersionMajor, Version.parseDelimitedFrom(_))
    val warehouseB = new KeyedWarehouse[Int, Version]("temp/version.dat", _.getVersionMajor, Version.parseDelimitedFrom(_))

    val version1 = Version.newBuilder.setVersionMajor(1)
                                     .setVersionMinor(2)
                                     .setVersionRevision(3).build
    val version2 = Version.newBuilder.setVersionMajor(2)
                                     .setVersionMinor(3)
                                     .setVersionRevision(4).build

    warehouseA.add(version1, false)
    warehouseA.add(version2, true)
    warehouseB.load()

    val retrieved1 = warehouseB(1)
    val retrieved2 = warehouseB(2)

    assert(warehouseB.all.size == 2)
    assert(retrieved1.getVersionMajor == version1.getVersionMajor &&
           retrieved1.getVersionMinor == version1.getVersionMinor &&
           retrieved1.getVersionRevision == version1.getVersionRevision)
    assert(retrieved2.getVersionMajor == version2.getVersionMajor &&
           retrieved2.getVersionMinor == version2.getVersionMinor &&
           retrieved2.getVersionRevision == version2.getVersionRevision)
  }
}
