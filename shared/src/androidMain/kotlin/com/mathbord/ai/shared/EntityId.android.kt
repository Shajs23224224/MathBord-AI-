package com.mathbord.ai.shared

import java.util.UUID

actual fun newEntityId(): EntityId = EntityId(UUID.randomUUID().toString())
