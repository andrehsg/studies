package com.daitan.pocmiddleware.util;

import static java.lang.System.out;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

/**
 * Class used for connecting to Cassandra database.
 */
public class CassandraConnector {

  /** Cassandra Cluster. */
  private static Cluster cluster;
  /** Cassandra Session. */
  private static Session session;
  /**
   * Connect to Cassandra Cluster specified by provided node IP
   * address and port number.
   *  @param node Cluster node IP address.
   * @param port Port of cluster host.
   */
  public static void connect(final String node, final int port)
  {
    cluster = Cluster.builder()
        .withoutJMXReporting()
        .addContactPoint(node)
        .withPort(port)
        .withProtocolVersion(ProtocolVersion.V4).build();

    ProtocolVersion myCurrentVersion = cluster.getConfiguration()
        .getProtocolOptions()
        .getProtocolVersion();

    final Metadata metadata = cluster.getMetadata();
    out.printf("Connected to cluster: %s\n", metadata.getClusterName());
    for (final Host host : metadata.getAllHosts())
    {
      out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
          host.getDatacenter(), host.getAddress(), host.getRack());
    }
    session = cluster.connect();
  }
  /**
   * Provide my Session.
   *
   * @return My session.
   */
  public static Session getSession()
  {

    return session;
  }
  /** Close cluster. */
  public static void close() {
    cluster.close();
  }
}
