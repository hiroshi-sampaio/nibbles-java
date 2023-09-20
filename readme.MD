### com.sampaio.hiroshi.nibbles.core.EventsForeseer.foreseeEventsOnArena

| Current | Empty | Wall | Food | Snake 1 | Snake 2 | Description                                                                         |
|---------|-------|------|------|---------|---------|-------------------------------------------------------------------------------------|
| Empty   |       |      |      | Snake 1 |         | Snake 1 is going there                                                              |
| Empty   |       |      |      |         | Snake 2 | Snake 1 is going there                                                              |
| Empty   |       |      |      | Snake 1 | Snake 2 | Both snakes are going there -> headbutt                                             |
| Food    |       |      |      | Snake 1 |         | Snake 1 is going to eat                                                             |
| Food    |       |      |      |         | Snake 2 | Snake 1 is going to eat                                                             |
| Food    |       |      |      | Snake 1 | Snake 2 | Both snakes are going to eat -> headbutt                                            |
| Snake 1 | Empty |      |      |         |         | Snake 1 tail tip is leaving the place                                               |
| Snake 1 | Empty |      |      | Snake 1 |         | Snake 1 tail tip is leaving the place but it is also heading there                  |
| Snake 1 | Empty |      |      |         | Snake 2 | Snake 1 tail tip is leaving the place but Snake two is heading there                |
| Snake 1 | Empty |      |      | Snake 1 | Snake 2 | Snake 1 tail tip is leaving the place but both snakes are heading there -> headbutt |
| Snake 2 | Empty |      |      |         |         | Snake 2 tail tip is leaving the place                                               |
| Snake 2 | Empty |      |      | Snake 1 |         | Snake 2 tail tip is leaving the place but Snake one is heading there                |
| Snake 2 | Empty |      |      |         | Snake 2 | Snake 2 tail tip is leaving the place but it is also heading there                  |
| Snake 2 | Empty |      |      | Snake 1 | Snake 2 | Snake 2 tail tip is leaving the place but both snakes are heading there -> headbutt |
