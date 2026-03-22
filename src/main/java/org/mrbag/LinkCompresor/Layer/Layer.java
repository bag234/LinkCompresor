package org.mrbag.LinkCompresor.Layer;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Layer {
    String ip;
    LocalDateTime access;
}
