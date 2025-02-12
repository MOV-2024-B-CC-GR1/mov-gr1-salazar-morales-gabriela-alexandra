package com.example.ccgr12024b_gasm.ui.screens.profile.edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Composable para un campo editable en la pantalla de edición de perfil.
 *
 * @param value Valor actual del campo de texto.
 * @param icon Icono representativo del campo.
 * @param label Etiqueta descriptiva del campo.
 * @param enabled Indica si el campo puede ser editado (por defecto es `true`).
 * @param onValueChange Callback que se ejecuta cuando el usuario modifica el valor.
 */
@Composable
fun EditProfileField(
    value: String,
    icon: ImageVector,
    label: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFF8E8E93),
                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        if (isEditing && enabled) {
                            OutlinedTextField(
                                value = value,
                                onValueChange = onValueChange,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF4285F4),
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                singleLine = true
                            )
                        } else {
                            Text(
                                text = value,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                if (enabled) {
                    IconButton(
                        onClick = { isEditing = !isEditing }
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Guardar" else "Editar",
                            tint = Color(0xFF4285F4)
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(start = 56.dp),
                color = Color.LightGray
            )
        }
    }
}