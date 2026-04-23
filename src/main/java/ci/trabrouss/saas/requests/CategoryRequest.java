package ci.trabrouss.saas.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
  private String name;
  private String description;
}
