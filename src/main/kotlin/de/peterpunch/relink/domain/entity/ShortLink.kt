package de.peterpunch.relink.domain.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.net.URL
import java.time.OffsetDateTime

@Entity
@Table(name = "short_links", schema = "relink")
class ShortLink(

    @Column(nullable = false, unique = true, updatable = false)
    var hash: String,

    @Column(nullable = false, updatable = false)
    var destinationUrl: URL,

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,

    @Id
    @GeneratedValue(generator = "short_links_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "short_links_id_seq", allocationSize = 1, schema = "relink"
    )
    var id: Long? = null
) {
    override fun toString(): String {
        return "ShortLink(hash='$hash', destinationUrl=$destinationUrl, createdAt=$createdAt, id=$id)"
    }
}
