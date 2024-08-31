/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme

@Composable
fun RegisterIconButton(
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        contentPadding = PaddingValues(24.dp)
    ) {
        Icon(painter = icon, contentDescription = "")
    }
}


@PreviewLightDark
@Composable
fun RegisterIconButtonPrev() {
    SkillSnapTheme {
        RegisterIconButton(icon = painterResource(id = R.drawable.google_icon)) {

        }
    }
}
