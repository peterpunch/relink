INSERT INTO RELINK.SHORT_LINKS(ID, DESTINATION_URL, HASH, CREATED_AT)
VALUES (nextval('RELINK.short_links_id_seq'), 'https://example.com', 'ABCD', current_timestamp);
