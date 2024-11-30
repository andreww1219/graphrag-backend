// 3. Visualize Relationships between Entities
MATCH (e1:Entity)-[r:RELATES_TO]->(e2:Entity)
RETURN e1, r, e2
LIMIT 50;
